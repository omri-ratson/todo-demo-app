package io.quarkus.sample;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
public class Todo extends PanacheEntity {

    private static final String COMPLETED_STR = "completed";

    @NotBlank
    @Column(unique = true)
    public String title;

    public static final boolean COMPLETED = false;

    @Column(name = "ordering")
    public int order;

    @Schema(example="https://github.com/quarkusio/todo-demo-app")
    public String url;

    public static List<Todo> findNotCompleted() {
        return list(COMPLETED_STR, false);
    }

    public static List<Todo> findCompleted() {
        return list(COMPLETED_STR, true);
    }

    public static long deleteCompleted() {
        return delete(COMPLETED_STR, true);
    }

}
