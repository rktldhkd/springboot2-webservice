package com.jojoldu.book.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//별다른 설정없이 @SpringBootTest 사용할 경우 H2데이터베이스를 자동으로 실행해준다.
//H2 프로젝트는 인메모리 관계형 DB로 app 재시작시마다 초기화된다. 테스트, 로컬환경 실행 시, 이 DB를 사용한다.
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    /*
    * @After
    * ㄴJUnit 단위 테스트가 끝날때마다 수행되는 메소드를 지정.
    * ㄴ보통은 배포 전 전체테스트를 수행할 때 테스트간 데이터 침범을 막기위해 사용한다.
    * ㄴ여러 테스트가 동시에 수행되면 테스트용 DB인 H2에 데이터가 그대로 남아 다음 테스트 실행시 테스트 실패할수있다.
    * */
    @After
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기(){
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        //builder클래스를 사용하여 값 초기화함.
        //repository.save : insert/update 쿼리 실행. id값이 있으면 update, 없으면 insert
        postsRepository.save(
                Posts.builder()
                        .title(title)
                        .content(content)
                        .author("rch@gmail.com")
                        .build()
        );

        //when
        List<Posts> postsList = postsRepository.findAll(); //모든데이터조회.

        //then
        Posts posts = postsList.get(0); //첫번째 데이터조회.
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }
}
