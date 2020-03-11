package com.github.benchdoos.meetroom.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Manage page item
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ManagePage {

    /**
     * Displayed name
     */
    @NotBlank
    private String name;

    /**
     * Page path (from /manage/)
     */
    @NotBlank
    private String path;

    /**
     * Description of page
     */
    private String description;

    /**
     * Icon from font awesome
     * @see <a href="http://fontawesome.io/icons/">Font awesome page</a>
     */
    private String icon;
}
