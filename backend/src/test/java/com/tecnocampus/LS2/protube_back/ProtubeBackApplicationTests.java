package com.tecnocampus.LS2.protube_back;

import org.hibernate.cfg.Environment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest({
			"pro_tube.store.dir=c:",
			"pro_tube.load_initial_data=false"
	})

	class ProtubeBackApplicationTests {

		@Test
		void contextLoads() {
	}
	@Test
	void getVideos(){
		String path = "src/test/java/video-test";
	}
	/*@ExtendWith(MockitoExtension.class)
	class VideoServiceTest{

			@Mock
			Environment env;



	}*/
}