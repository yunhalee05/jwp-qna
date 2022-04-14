package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.NotFoundException;

@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    private User user;
    private Question question;
    private Answer firstAnswer;
    private Answer secondAnswer;

    @BeforeEach
    void setUp(){
        user = userRepository.save(new User("javajigi", "password", "name", "javajigi@slipp.net"));
        question = questionRepository.save(new Question("title1", "contents1").writeBy(user));
        firstAnswer = new Answer(user, question, "Answers Contents1");
        secondAnswer = new Answer(user, question, "Answers Contents2");
    }

    @Test
    @DisplayName("questionId를 이용하여 삭제되지 않은 answer 리스트를 반환한다.")
    void find_not_deleted_answer_with_question_id() {
        answerRepository.saveAll(Arrays.asList(firstAnswer, secondAnswer));
        List<Answer> answers = answerRepository.findByQuestionIdAndDeletedFalse(question.getId());
        for (Answer answer : answers) {
            assertThat(answer.getQuestion().getId().equals(question.getId())).isTrue();
            assertThat(answer.isDeleted()).isFalse();
        }
    }

    @Test
    @DisplayName("answerId를 이용하여 삭제되지 않은 answer를 반환한다.")
    void find_not_deleted_answer_with_id() {
        Answer requestedAnswer = answerRepository.save(firstAnswer);
        Answer foundAnswer = answerRepository.findByIdAndDeletedFalse(requestedAnswer.getId())
            .orElseThrow(NotFoundException::new);

        assertThat(requestedAnswer.equals(foundAnswer)).isTrue();
    }

}