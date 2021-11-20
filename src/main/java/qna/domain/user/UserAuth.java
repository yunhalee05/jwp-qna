package qna.domain.user;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import qna.common.exception.ErrorMessage;
import qna.common.exception.InvalidParamException;
import qna.common.exception.UnAuthorizedException;

@Embeddable
public class UserAuth {

    @Column(name = "user_id", unique = true, length = 20, nullable = false)
    private String userId;

    @Column(name = "password", length = 20, nullable = false)
    private String password;

    protected UserAuth() {
    }

    public UserAuth(String userId, String password) {
        valid(userId, password);

        this.userId = userId;
        this.password = password;
    }


    public void updateValid(UserAuth loginUserAuth, UserAuth changeUserAuth) {
        if (!matchUserId(loginUserAuth.userId)) {
            throw new UnAuthorizedException(
                ErrorMessage.UNAUTHORIZED_EXCEPTION_USER_ID_NOT_SAME_EXCEPTION_MESSAGE);
        }

        if (!matchPassword(changeUserAuth.password)) {
            throw new UnAuthorizedException(
                ErrorMessage.UNAUTHORIZED_EXCEPTION_MISS_MATCH_PASSWORD_EXCEPTION_MESSAGE);
        }
    }

    public void changeUserId(String changeUserId) {
        this.userId = changeUserId;
    }

    public String getUserId() {
        return userId;
    }

    private void valid(String userId, String password) {
        if (Objects.isNull(userId) || Objects.isNull(password)) {
            throw new InvalidParamException("모든 값이 존재해야 합니다.");
        }
    }

    private boolean matchUserId(String userId) {
        return this.userId.equals(userId);
    }

    private boolean matchPassword(String targetPassword) {
        return this.password.equals(targetPassword);
    }


    @Override
    public String toString() {
        return "UserAuth{" +
            "userId='" + userId + '\'' +
            ", password='" + password + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserAuth userAuth = (UserAuth) o;
        return Objects.equals(userId, userAuth.userId)
            && Objects.equals(password, userAuth.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, password);
    }
}