import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

/**
 * @author Jeferson Martins @Capgemini
 * 22/03/2022
 */

public class MonoTest {

    @Test
    void firstMono(){
        Mono.just("Hello World!");
    }
    @Test
    void monoWithConsumer(){
        Mono.just("Hello World!")
                .log()
                .subscribe(s -> System.out.println(s));
    }

    @Test
    void monoWithDoOn(){
        Mono.just("Hello World!")
                .log()
                .doOnSubscribe(subscription -> System.out.println("Subscribed: " + subscription))
                .doOnRequest(request -> System.out.println("Requested: " + request))
                .doOnSuccess(complete -> System.out.println("Completed: " + complete))
                .subscribe(System.out::println);
    }

    @Test
    void emptyMono(){
        Mono.empty()
                .log()
                .subscribe(System.out::println, null , () -> System.out.println("Completed"));
    }

    @Test
    void errorRuntimeExceptionMono(){
        Mono.error(new RuntimeException())
                .log()
                .subscribe();
    }

    @Test
    void errorExceptionMono(){
        Mono.error(new Exception())
                .log()
                .subscribe();
    }

    @Test
    void errorConsumerMono(){
        Mono.error(new Exception())
                .log()
                .subscribe(System.out::println,
                            e -> System.out.println("Error: " + e));
    }

    @Test
    void errorDoOnErrorMono(){
        Mono.error(new Exception())
                .doOnError(e -> System.out.println("Error: " + e))
                .log()
                .subscribe();
    }

    @Test
    void errorOnErrorResumeMono(){
        Mono.error(new Exception())
                .onErrorResume(e -> {
                    System.out.println("Error: " + e);
                    return Mono.just("Hello World!");
                })
                .log()
                .subscribe();
    }

    @Test
    void errorOnErrorReturnMono(){
        Mono.error(new Exception())
                .onErrorReturn("Hello World!")
                .log()
                .subscribe();
    }



}
