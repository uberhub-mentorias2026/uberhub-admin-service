package iftm.microservice.admin_service.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity 
@Table(
    name = "ADMIN_MENTOR_TAGS",
    indexes = {
        @Index(name = "IX_ADMIN_TAGS_TAG", columnList = "TAG_ID")
    }
)
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
    private Mentor mentor;

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

    public AdminMentorTags() {
    }

    public AdminMentorTags(Mentor mentor, AdminTags tag) {
        this.mentor = mentor;
        this.tag = tag;
        this.id = new AdminMentorTagId(mentor.getId(), tag.getId());
    }

    public AdminMentorTagId getId() {
        return id;
    }

    public void setId(AdminMentorTagId id) {
        this.id = id;
    }

    public Mentor getMentor() {
        return mentor;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }

    public AdminTags getTag() {
        return tag;
    }

    public void setTag(AdminTags tag) {
        this.tag = tag;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}