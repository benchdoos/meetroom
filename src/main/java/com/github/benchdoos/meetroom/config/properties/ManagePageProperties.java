package com.github.benchdoos.meetroom.config.properties;

import com.github.benchdoos.meetroom.domain.ManagePage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/***
 * Manage page properties. Defines what {@link ManagePage} to show
 */
@Getter
@Setter
@Validated
@Component
@ConfigurationProperties("manage-pages")
public class ManagePageProperties {

    @NotEmpty
    private List<ManagePage> pages;

}
