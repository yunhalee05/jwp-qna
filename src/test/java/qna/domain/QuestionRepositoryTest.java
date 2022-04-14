package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.NotFoundException;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserRepository userRepository;

    private User user;
    private Question firstQuestion;
    private Question secondQuestion;

    @BeforeEach
    void setUp(){
        user = userRepository.save(new User("javajigi", "password", "name", "javajigi@slipp.net"));
        firstQuestion = new Question("title1", "contents1").writeBy(user);
        secondQuestion = new Question("title2", "contents2").writeBy(user);
    }

    @Test
    @DisplayName("삭제되지 않은 question 리스트를 반환한다.")
    void find_not_deleted_question() {
        questionRepository.saveAll(Arrays.asList(firstQuestion, secondQuestion));
        List<Question> questions = questionRepository.findByDeletedFalse();
        for (Question question : questions) {
            assertThat(question.isDeleted()).isFalse();
        }
    }


    @Test
    @DisplayName("questionId를 이용하여 삭제되지 않은 question을 반환한다.")
    void find_not_deleted_question_with_question_id() {
        Question requestedQuestion = questionRepository.save(firstQuestion);
        Question foundQuestion = questionRepository
            .findByIdAndDeletedFalse(requestedQuestion.getId())
            .orElseThrow(NotFoundException::new);
        assertThat(requestedQuestion.equals(foundQuestion)).isTrue();

    }
}