package iftm.microservice.admin_service.domain;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AdminMentorTagId implements Serializable {

    @Column(name = "MENTOR_ID")
    private Long mentorId; 

    @Column(name = "TAG_ID")
    private Long tagId;

}