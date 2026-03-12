package com.shreyas.search.document;

import com.shreyas.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "tasks")
public class TaskDocument {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Keyword)
    private String imageURL;

    @Field(type = FieldType.Keyword)
    private String createdBy;

    @Field(type = FieldType.Keyword)
    private String modifiedBy;

    @Field(type = FieldType.Keyword)
    private String assignedUserId;

    @Field(type = FieldType.Keyword)
    private String status;

    @Field(type = FieldType.Keyword)
    private List<String> tags;

    @Field(type = FieldType.Date, format = DateFormat.date_optional_time)
    private LocalDateTime deadLine;

    @Field(type = FieldType.Date, format = DateFormat.date_optional_time)
    private LocalDateTime createdAt;

    public static TaskDocument fromTask(Task task) {
        return TaskDocument.builder()
                .id(task.getId().toString())
                .title(task.getTitle())
                .description(task.getDescription())
                .imageURL(task.getImageURL())
                .createdBy(task.getCreatedBy() != null ? task.getCreatedBy().toString() : null)
                .modifiedBy(task.getModifiedBy() != null ? task.getModifiedBy().toString() : null)
                .assignedUserId(task.getAssignedUserId() != null ? task.getAssignedUserId().toString() : null)
                .status(task.getStatus())
                .tags(task.getTags() != null ? task.getTags() : Collections.emptyList())
                .deadLine(task.getDeadLine())
                .createdAt(task.getCreatedAt())
                .build();
    }

    public UUID getUuid() {
        return id == null ? null : UUID.fromString(id);
    }
}
