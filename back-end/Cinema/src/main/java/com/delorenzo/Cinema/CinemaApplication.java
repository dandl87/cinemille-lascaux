package com.delorenzo.Cinema;

import com.delorenzo.Cinema.conf.DataSourceProperties;
import com.delorenzo.Cinema.entity.Room;
import com.delorenzo.Cinema.exception.DataRetrievingFromExcelException;
import com.delorenzo.Cinema.logic.Scheduler;
import com.delorenzo.Cinema.logic.Slot;
import com.delorenzo.Cinema.repository.RoomRepository;
import com.delorenzo.Cinema.service.MainService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;


@EnableConfigurationProperties(DataSourceProperties.class)
@SpringBootApplication
public class CinemaApplication {




    public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(CinemaApplication.class, args);
		MainService mainService = context.getBean(MainService.class);
		RoomRepository roomRepository = context.getBean(RoomRepository.class);

		Scheduler imaxScheduler = (Scheduler) context.getBean("imaxScheduler");
		List<Room> imaxRoom = roomRepository.findAllByImax(true);

		assignRoomToScheduler(imaxScheduler,imaxRoom);

		Scheduler regularScheduler = (Scheduler) context.getBean("regularScheduler");
		List<Room> regularRoom = roomRepository.findAllByImax(false);

		assignRoomToScheduler(regularScheduler,regularRoom);

		// first job to create a first
		mainService.initializationBatch();

		//   potential IO Exception

        try {
            mainService.weeklyBatch();
        } catch (DataRetrievingFromExcelException e) {
            throw new RuntimeException(e);
        }

    }


	private static void assignRoomToScheduler(Scheduler scheduler, List<Room> rooms) {
		Slot[] slots = scheduler.getSlots();
		for(int i = 0; i < slots.length; i++) {
			slots[i]= new Slot();
			slots[i].setRoom(rooms.get(i));
		}
	}

}
