package com.roms.module.translation.domain.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.roms.library.datatype.serializer.JsonDateTimeSerializer;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.joda.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(
        name="translations",
        uniqueConstraints= @UniqueConstraint(columnNames={"language", "code"})
)
public class Translation {

    public enum AvailableLanguage {
        FR("fr"),
        EN("en");

        private String code;

        AvailableLanguage(String code) {
            this.code = code;
        }

        public String code() {
            return this.code;
        }
    }

	@Id
	@Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="code")
	@Length(max = 50)
	@NotNull
	private String code;

    @Column(name="language")
    @Enumerated(EnumType.STRING)
    @NotNull
	private AvailableLanguage language;

    @Column(name="value")
    @NotNull
    private String value;

    @Column(name="created_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    @JsonSerialize(using=JsonDateTimeSerializer.class)
    @NotNull
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    @JsonSerialize(using=JsonDateTimeSerializer.class)
    @NotNull
    private LocalDateTime updatedAt;

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AvailableLanguage getLanguage() {
        return language;
    }

    public void setLanguage(AvailableLanguage language) {
        this.language = language;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
