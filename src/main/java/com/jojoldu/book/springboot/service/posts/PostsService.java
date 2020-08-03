package com.jojoldu.book.springboot.service.posts;

import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
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
}
