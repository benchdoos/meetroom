package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.config.properties.InternalConfiguration;
import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.UserEmailUpdateRequest;
import com.github.benchdoos.meetroom.exceptions.UserEmailUpdateRequestNotFoundOrNotActiveException;
import com.github.benchdoos.meetroom.repository.UserEmailUpdateRequestRepository;
import com.github.benchdoos.meetroom.repository.UserRepository;
import com.github.benchdoos.meetroom.service.UserEmailUpdateService;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Default email update service
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserEmailUpdateServiceImpl implements UserEmailUpdateService {
    private final UserEmailUpdateRequestRepository userEmailUpdateRequestRepository;
    private final UserRepository userRepository;
    private final InternalConfiguration internalConfiguration;

    @Override
    public UserEmailUpdateRequest createEmailUpdateRequest(User user, String newEmailAddress) {
        final ZonedDateTime requestTime = ZonedDateTime.now();

        deactivateAllPreviousUserEmailUpdateRequests(user);

        final UserEmailUpdateRequest.UserEmailUpdateRequestBuilder builder = UserEmailUpdateRequest.builder()
                .requestedFor(user)
                .requested(requestTime)
                .expires(requestTime.plusDays(internalConfiguration.getUserSettings().getEmailUpdateRequestExpiresInDays()))
                .newEmailAddress(newEmailAddress)
                .newEmailAddressActivated(false) //important
                .newEmailConfirmation(UUID.randomUUID())
                .active(true);

        if (StringUtils.isEmpty(user.getEmail())) {
            builder.oldEmailConfirmation(null); // prevents problems if user has not email
            builder.oldEmailAddressActivated(true);
        } else {
            builder.oldEmailConfirmation(UUID.randomUUID());
            builder.oldEmailAddressActivated(false);
        }

        return userEmailUpdateRequestRepository.save(builder.build());
    }

    @Transactional
    @Override
    public UserEmailUpdateRequest submitEmailRequest(UUID emailId) {
        final ZonedDateTime requestTime = ZonedDateTime.now();

        final Specification<UserEmailUpdateRequest> specification = prepareSpecificationForEmailId(emailId, requestTime);
        final UserEmailUpdateRequest request = userEmailUpdateRequestRepository.findOne(specification) // more records not expected
                .orElseThrow(UserEmailUpdateRequestNotFoundOrNotActiveException::new);

        if (request.getOldEmailConfirmation() != null) {
            if (request.getOldEmailConfirmation().equals(emailId)) {
                request.setOldEmailAddressActivated(true);
            }
        } else {
            request.setOldEmailAddressActivated(true);
        }

        if (request.getNewEmailConfirmation().equals(emailId)) {
            request.setNewEmailAddressActivated(true);
        }

        if (request.isOldEmailAddressActivated() && request.isNewEmailAddressActivated()) {
            final User requestedFor = request.getRequestedFor();
            log.info("Updating email for user: {}, new address: {}", requestedFor.getUsername(), request.getNewEmailAddress());
            request.setActive(false);

            requestedFor.setEmail(request.getNewEmailAddress());

            userRepository.save(requestedFor);
        }

        return userEmailUpdateRequestRepository.save(request);
    }

    /**
     * Update all user's previous email update requests to make them deactivated
     *
     * @param user user
     */
    private void deactivateAllPreviousUserEmailUpdateRequests(User user) {
        final Collection<UserEmailUpdateRequest> allByRequestedFor = userEmailUpdateRequestRepository.findAllByRequestedFor(user);

        if (!CollectionUtils.isEmpty(allByRequestedFor)) {
            allByRequestedFor.forEach(request -> request.setActive(false));
            userEmailUpdateRequestRepository.saveAll(allByRequestedFor);
        }
    }

    /**
     * Prepare specification to find user update email request by one of emails ids
     *
     * @param emailId id of email
     * @param requestTime time to count expiration
     * @return specification
     */
    private Specification<UserEmailUpdateRequest> prepareSpecificationForEmailId(UUID emailId, ZonedDateTime requestTime) {
        return (root, criteriaQuery, criteriaBuilder) -> {

            final List<Predicate> emailsPredicate = new ArrayList<>();
            emailsPredicate.add(criteriaBuilder.equal(root.get("oldEmailConfirmation"), emailId));
            emailsPredicate.add(criteriaBuilder.equal(root.get("newEmailConfirmation"), emailId));

            final Predicate betweenPredicate = criteriaBuilder.between(
                    criteriaBuilder.literal(requestTime),
                    root.get("requested"),
                    root.get("expires"));

            final Predicate activePredicate = criteriaBuilder.and(
                    criteriaBuilder.isNotNull(root.get("active").as(Boolean.class)),
                    criteriaBuilder.isTrue(root.get("active").as(Boolean.class)));

            return criteriaBuilder.and(
                    criteriaBuilder.or(emailsPredicate.toArray(new Predicate[0])),
                    betweenPredicate,
                    activePredicate
            );
        };
    }
}
