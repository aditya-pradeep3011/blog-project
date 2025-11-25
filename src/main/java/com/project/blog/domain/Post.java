package com.project.blog.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "T_POST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;
	
	@Column(nullable = false)
	private Integer readTime;
	
	@Column(nullable = false)
	private LocalDateTime createdAt;
	
	@Column(nullable = false)
	private LocalDateTime updatedAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
	private User author;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PostStatus postStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
	private Category category;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "POST_TAG",
			joinColumns = @JoinColumn(name = "post_id"),
			inverseJoinColumns = @JoinColumn(name = "tag_id")
	)
	@Builder.Default
	private Set<Tag> tags = new HashSet<>();
	
	@PrePersist
	protected void onCreate()
	{
		LocalDateTime curr = LocalDateTime.now();
		this.createdAt = curr;
		this.updatedAt = curr;
	}
	
	@PreUpdate
	protected void onUpdate()
	{
		LocalDateTime curr = LocalDateTime.now();
		this.updatedAt = curr;
	}

	@Override
	public int hashCode() {
		return Objects.hash(author, category, content, createdAt, id, postStatus, readTime, tags, title, updatedAt);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		return Objects.equals(author, other.author) && Objects.equals(category, other.category)
				&& Objects.equals(content, other.content) && Objects.equals(createdAt, other.createdAt)
				&& Objects.equals(id, other.id) && postStatus == other.postStatus
				&& Objects.equals(readTime, other.readTime) && Objects.equals(tags, other.tags)
				&& Objects.equals(title, other.title) && Objects.equals(updatedAt, other.updatedAt);
	}
}
