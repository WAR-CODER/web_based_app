package wethinkcode.httpapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.javalin.Javalin;
import io.javalin.http.*;
import io.javalin.plugin.json.JsonMapper;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Exercise 1
 * <p>
 * Application Server for the Tasks API
 */
public class TasksAppServer {
    private static final TasksDatabase database = new TasksDatabase();

    private final Javalin appServer;

    /**
     * Create the application server and configure it.
     */
    public TasksAppServer() {
        this.appServer = Javalin.create(config -> {
            config.defaultContentType = "application/json";
            config.jsonMapper(createGsonMapper());
        });

        this.appServer.get("/tasks/", this::getAllTasks);
        this.appServer.get("task/{id}",this::getOneTasks);
        this.appServer.post("task/", this::addTask);



    }

    private void addTask(Context context){
        Task task = context.bodyAsClass(Task.class);
        boolean results = database.add(task);
        if(results == true) {
            context.header("location", "/task/" + task.getId());
            context.status(HttpCode.CREATED);
            context.json(task);
        }else{
            throw new BadRequestResponse("HTTP Bad request");
        }
        context.json(task);





    }

    /**
     * Use GSON for serialisation instead of Jackson
     * because GSON allows for serialisation of objects without no args constructors.
     *
     * @return A JsonMapper for Javalin
     */
    private static JsonMapper createGsonMapper() {
        Gson gson = new GsonBuilder().create();
        return new JsonMapper() {
            @NotNull
            @Override
            public String toJsonString(@NotNull Object obj) {
                return gson.toJson(obj);
            }

            @NotNull
            @Override
            public <T> T fromJsonString(@NotNull String json, @NotNull Class<T> targetClass) {
                return gson.fromJson(json, targetClass);
            }
        };
    }

    /**
     * Start the application server
     *
     * @param port the port for the app server
     */
    public void start(int port) {
        this.appServer.start(port);
    }

    /**
     * Stop the application server
     */
    public void stop() {
        this.appServer.stop();
    }

    /**
     * Get all tasks
     *
     * @param context the server context
     */
    private void getAllTasks(Context context) {
        context.contentType("application/json");
        context.json(database.all());
    }
    private void getOneTasks(Context context) {
        Integer id = context.pathParamAsClass("id", Integer.class).get();
        context.contentType("application/json");
        Task task = database.get(id);
//        context.json(database.get(1));
        if (task == null) {
            throw new NotFoundResponse("Not Found" + context);
        }
        context.json(task);

    }
}
