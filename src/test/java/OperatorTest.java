import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author Jeferson Martins @Capgemini
 * 22/03/2022
 */

public class OperatorTest {

    @Test
    void map() {
        //Multiplicando o valor de cada item do fluxo por 10 e imprimindo na tela
        Flux.range(1, 5)
                .map(i -> this.valueMutiply(i, 10))
                .subscribe(System.out::println);
    }

    @Test
    void flatMap() {
        Flux.range(1, 5)
                .flatMap(i -> Flux.range(this.valueMutiply(i,10), 2))
                .subscribe(System.out::println);
    }

    @Test
    void flatMapMany(){
        Mono.just(5)
                .flatMapMany(i -> Flux.range(1, i))
                .log()
                .subscribe(System.out::println);
    }

    @Test
    void concat() throws InterruptedException {
        Flux<Integer> oneToFive = Flux.range(1, 5)
                .delayElements(Duration.ofMillis(200));
        Flux<Integer> sixToTen = Flux.range(6, 5)
                .delayElements(Duration.ofMillis(400));

        Flux.concat(oneToFive, sixToTen)
                .log()
                .subscribe(System.out::println);

        Thread.sleep(TimeUnit.SECONDS.toMillis(4));
    }

    @Test
    void merge() throws InterruptedException {
        Flux<Integer> oneToFive = Flux.range(1, 5)
                .delayElements(Duration.ofMillis(200));
        Flux<Integer> sixToTen = Flux.range(6, 5)
                .delayElements(Duration.ofMillis(400));

        Flux.merge(oneToFive, sixToTen)
                .log()
                .subscribe(System.out::println);

        Thread.sleep(TimeUnit.SECONDS.toMillis(4));
    }

    @Test
    void zip(){
        Flux.zip(
                Flux.range(1, 5),
                Flux.range(6, 5),
                (a, b) -> a + ", " + b
        ).subscribe(System.out::println);

        //ZipWith

    }

     private Integer valueMutiply(Integer value , Integer multiplier){
        return value * multiplier;
    }
}
