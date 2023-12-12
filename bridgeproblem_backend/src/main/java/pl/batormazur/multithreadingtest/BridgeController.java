package pl.batormazur.multithreadingtest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
@AllArgsConstructor
public class BridgeController {
    private List<Car> cars;
    private final BridgeService bridgeService;

    @GetMapping
    public ResponseEntity<List<Car>> getCars() {
        return ResponseEntity.ok(cars);
    }

    @PostMapping
    public void addCar(@RequestBody CarAddRequest carRequest) {
        var car = new Car(carRequest.getName(), State.WAITING, carRequest.getSource(), carRequest.getTimeMilliseconds());
        //var car = new Car(carRequest.getName(), State.WAITING, carRequest.getSource(), ThreadLocalRandom.current().nextInt(1000, 10000 + 1));
        cars.add(car);
    }

    @Scheduled(fixedDelay = 1)
    public void process() {
        bridgeService.process(cars);
    }
}