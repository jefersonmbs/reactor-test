import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Jeferson Martins @Capgemini
 * 22/03/2022
 */

public class FluxTest {

    @Test
    void firstFlux() {
        Flux.just("Hello", "World", "!").log().subscribe();
    }

    @Test
    void fluxFromInterable() {
        Flux.fromIterable(Arrays.asList("Hello", "World", "!")).log().subscribe();
    }

    @Test
    void fluxFromRage() {
        Flux.range(5, 10).log().subscribe();
    }

    @Test
    void fluxFronInterval() throws InterruptedException {
        //Star 0, interval 1/2 s,
        Flux.interval(Duration.ofMillis(500)).take(5).log().subscribe();
        Thread.sleep(SECONDS.toMillis(5));
    }

    @Test
    void fluxRequest() {
        Flux.range(1, 10).log().subscribe(new BaseSubscriber<>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                subscription.request(3);
            }
        });
    }

    @Test
    void fluxCustomSubscriber() {
        Flux.range(1, 10).log().subscribe(new BaseSubscriber<>() {
            int elementsToProcess = 3;
            int count = 0;

            public void hookOnSubscribe(Subscription subscription) {
                System.out.println("Subscribed!");
                request(elementsToProcess);
            }

            public void hookOnNext(Integer value) {
                count++;
                if (Objects.equals(count, elementsToProcess)) {
                    count = 0;
                    Random random = new Random();
                    elementsToProcess = random.ints(1, 4).findFirst().getAsInt();
                    System.out.printf("Requesting %d elements\n", elementsToProcess);
                    request(elementsToProcess);
                }
            }
        });
    }

    @Test
    void fluxLimitRate(){
        Flux.range(1, 10)
                .log()
                .limitRate(3)
                .subscribe();
    }


}
