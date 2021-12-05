package com.galaxyit.state;

import com.galaxyit.model.Car;
import com.galaxyit.service.CarService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
@Profile("prod")
public class StateManager {

    private final String filePath = "./cars.json";

    private static final Logger LOGGER = LoggerFactory.getLogger(StateManager.class);

    @Autowired
    private CarService carService;

    private final Gson gson = new Gson();

    private File file;

    public StateManager() throws IOException {
        if (!new File(filePath).exists()) {
            file = new File(filePath);
            file.createNewFile();
        } else {
            file = new File(filePath);
        }
    }

    @PreDestroy
    public void saveAllToDisk() throws IOException {
        file = new File(filePath);
        Iterable<Car> allCars = carService.fetchAllCars();
        String json = gson.toJson(allCars);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true));
        writer.append(json);
        writer.close();
    }

    @PostConstruct
    public void readFromDiskIsAny() {
        Reader reader;
        try {
            reader = Files.newBufferedReader(Paths.get(file.getAbsolutePath()));
            List<Car> cars = gson.fromJson(reader, new TypeToken<List<Car>>() {
            }.getType());
            if (cars != null && !cars.isEmpty()) {
                carService.saveAll(cars);
            }
            file.delete();
        } catch (IOException e) {
            LOGGER.error("Error fetching reading file");
            e.printStackTrace();
        }
    }
}
