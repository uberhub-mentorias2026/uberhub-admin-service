package iftm.microservice.admin_service.domain;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "ADMIN_MENTOR_TAGS",
    indexes = {
        @Index(name = "IX_ADMIN_MENTOR_TAGS_TAG", columnList = "TAG_ID")
    }
)


@Getter
@Setter
@NoArgsConstructor
public class AdminMentorTags {

    @EmbeddedId
    private AdminMentorTagId id;

    @ManyToOne
    @MapsId("mentorId")
    @JoinColumn(
        name = "MENTOR_ID",
        nullable = false,
        foreignKey = @ForeignKey(name = "FK_ADMIN_MENTOR_TAGS_MENTOR")
    )
    private AdminMentores mentor; 

    @ManyToOne
    @MapsId("tagId")
    @JoinColumn(
        name = "TAG_ID",
        nullable = false,
        foreignKey = @ForeignKey(name = "FK_ADMIN_MENTOR_TAGS_TAG")
    )
    private AdminTags tag;

    @CreationTimestamp
    @Column(name = "CRIADO_EM", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    public AdminMentorTags(AdminMentores mentor, AdminTags tag) {
        this.mentor = mentor;
        this.tag = tag;
        this.id = new AdminMentorTagId(mentor.getId(), tag.getId()); 
    }

}