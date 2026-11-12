package iftm.microservice.admin_service.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable 
public class AdminMentorTagId implements Serializable {
    
    @Column(name = "MENTOR_ID")
    private Long mentorId;

    @Column(name = "TAG_ID")
    private Long tagId;

    public AdminMentorTagId() {
    }

    public AdminMentorTagId(Long mentorId, Long tagId) {
        this.mentorId = mentorId;
        this.tagId = tagId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminMentorTagId that = (AdminMentorTagId) o;
        return Objects.equals(mentorId, that.mentorId) && Objects.equals(tagId, that.tagId);
    }

    @Override 
    public int hashCode() {
        return Objects.hash(mentorId, tagId);
    }

    public Long getMentorId() {
        return mentorId;
    }

    public void setMentorId(Long mentorId) {
        this.mentorId = mentorId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}