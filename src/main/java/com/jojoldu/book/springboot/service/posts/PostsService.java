package com.jojoldu.book.springboot.service.posts;

import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.web.dto.PostsListResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    //@RequiredArgsConstructor에 의해서 @Autowired 안해도 생성자방식으로 객체가 초기화, 형성된다
    private final PostsRepository postsRepository;

    //게시글 입력
    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        //Posts entity사용하여 db에 데이터 저장 후 id값 호출.
        return postsRepository.save(requestDto.toEntity()).getId();
    }//save


    /*
    * 트랜잭션 안에서 엔티티 값 변경 시, 트랜잭션이 끝날 때 변경분이 자동 반영된다.
    * 그래서 아래의 코드에 save()가 없고 posts 엔티티의 값을 update로 변경만함. 이러면 트랜잭션이 끝날때
    * 자동반영된다고 한다.
    * 굳이 save() 사용하려면 .update() 아래에 -> postsRepository.save(posts)
    * */
    //게시글 수정
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                                            new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    //게시글 단건 조회
    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(() ->
                                            new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }

    //게시글 전체 조회
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        //findAllDesc()로 List<Posts> 값 묶음 받아옴.
        //map 안의 객체명::new 는 람다식이다. .map(posts -> new PostsListResponseDto(posts)) 와 같다.
        //기존의 자바컬렉션,배열등을 가공할 때 for/foreach등으로 하나씩 골라 가공했다면
        //자바8의 stream 기능을 사용하여 람다함수형식으로 깔끔하게처리 가능\\
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }
}
