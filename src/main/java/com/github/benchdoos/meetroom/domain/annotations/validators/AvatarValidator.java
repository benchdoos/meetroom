package com.github.benchdoos.meetroom.domain.annotations.validators;

import com.github.benchdoos.meetroom.domain.annotations.ValidAvatar;
import com.github.benchdoos.meetroom.domain.interfaces.AvatarInfo;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class AvatarValidator implements ConstraintValidator<ValidAvatar, AvatarInfo> {

    @Override
    public void initialize(ValidAvatar constraint) {
    }

    @Override
    public boolean isValid(AvatarInfo avatarInfo, ConstraintValidatorContext constraintValidatorContext) {
        if (avatarInfo.getType() == null) return false;
        if (StringUtils.isBlank(avatarInfo.getData())) return false;

        switch (avatarInfo.getType()) {
            case GRAVATAR:
                final EmailValidator emailValidator = new EmailValidator();
                final boolean valid = emailValidator.isValid(avatarInfo.getData(), null);
                if (!valid) {
                    return false;
                }

                break;
            case BASE64:
//                try {
//                    final byte[] bytes = Base64Utils.decodeFromString(avatarInfo.getData()); //todo: fix illegal base64 3a
//                } catch (final Exception e) {
//                    log.warn("Given image is illegal", e);
//                    return false;
//                }
                final String[] strings = avatarInfo.getData().split(",", 2);
                final String prefix = strings[0];
                switch (prefix) {//check image's extension
                    case "data:image/jpeg;base64":
                    case "data:image/png;base64":
                        break;
                    default:
                        log.warn("Given avatar base64 data type is unsupported: {}", prefix);
                        return false; //not supported
                }
                break;
        }
        return true;
    }

}
