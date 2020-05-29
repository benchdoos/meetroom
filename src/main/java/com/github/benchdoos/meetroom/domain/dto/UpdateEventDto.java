package com.github.benchdoos.meetroom.domain.dto;

import com.github.benchdoos.meetroom.domain.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * DTO to update {@link Event}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateEventDto {

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date fromDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date toDate;

    @Nullable
    @Size(max = 256)
    private String title;

    @Nullable
    @Size(max = 3000)
    private String description;

}
