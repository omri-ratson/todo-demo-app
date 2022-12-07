package io.quarkus.sample;

import io.quarkus.panache.common.Sort;

import javax.validation.Valid;
import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import static io.quarkus.hibernate.orm.panache.PanacheEntityBase.findById;
import static io.quarkus.hibernate.orm.panache.PanacheEntityBase.listAll;

@GraphQLApi
public class TodoGraphQLEndpoint {

    @Query
    @Description("Get all the todos")
    public List<Todo> getAllTodos() {
        return listAll(Sort.by("order"));
    }

    @Query
    @Description("Get a specific todo by id")
    public Todo getTodo(Long id) {
        return findById(id);
    }

    @Mutation
    @Description("Create a new todo")
    public Todo create(@Valid Todo item) {
        item.persist();
        return item;
    }

    @Mutation
    @Description("Update an exiting todo")
    public Todo update(@Valid Todo todo, Long id) {
        Todo entity = findById(id);
        entity.id = id;
        entity.completed = todo.completed;
        entity.order = todo.order;
        entity.title = todo.title;
        entity.url = todo.url;
        return entity;
    }

    @Mutation
    @Description("Remove all completed todos")
    public List<Todo> deleteCompleted() {
        List<Todo> completed = Todo.findCompleted();
        Todo.deleteCompleted();
        return completed;
    }

    @Mutation
    @Description("Delete a specific todo")
    public Todo deleteTodo(Long id) {
        Todo entity = findById(id);
        if (entity == null) {
            return null;
        }
        entity.delete();
        return entity;
    }
}