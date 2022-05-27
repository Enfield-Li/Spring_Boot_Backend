package com.example.reddit.user;

import com.example.reddit.mapper.UserPostMapper;
import com.example.reddit.mapper.source.userPost.UserPostInfoWithInteractions;
import com.example.reddit.mapper.source.userPost.UserPostInfoWithoutInteractions;
import com.example.reddit.mapper.target.userPost.UserPaginatedPosts;
import com.example.reddit.mapper.target.userPost.UserPostAndInteractions;
import com.example.reddit.user.dto.request.CreateUserDto;
import com.example.reddit.user.dto.request.LoginUserDto;
import com.example.reddit.user.dto.response.ResUser;
import com.example.reddit.user.dto.response.ResUserError;
import com.example.reddit.user.dto.response.UserInfo;
import com.example.reddit.user.dto.response.UserProfileRO;
import com.example.reddit.user.dto.response.UserRO;
import com.example.reddit.user.entity.Password;
import com.example.reddit.user.entity.User;
import com.example.reddit.user.repository.UserMapper;
import com.example.reddit.user.repository.UserRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpSession;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Autowired
  UserService(
    UserRepository userRepository,
    PasswordEncoder passwordEncoder,
    UserMapper userMapper
  ) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.userMapper = userMapper;
  }

  public User getUserInfo(Long id, Long meId) {
    User user = userRepository
      .findById(id)
      .orElseThrow(NoSuchElementException::new);

    /* 
      用户邮箱只对自己可见
      User can only see their own email
     */
    Boolean isNotOneSelf = user.getId() != meId;
    if (isNotOneSelf) user.setEmail(null);

    return user;
  }

  public UserRO login(LoginUserDto dto, HttpSession session) {
    try {
      User user;

      String usernameOrEmail = dto.getUsernameOrEmail();

      /* 
        检查用户是否存在 
        Check if user exist
       */
      Boolean isEmail = usernameOrEmail.contains("@");

      if (isEmail) {
        user =
          userRepository
            .findByEmail(usernameOrEmail)
            .orElseThrow(NoSuchElementException::new);
      } else {
        user =
          userRepository
            .findByUsername(usernameOrEmail)
            .orElseThrow(NoSuchElementException::new);
      }

      /* 
        核对密码 
        Check password
       */
      Boolean passwordIsCorrect = !user
        .getPassword()
        .matchPassword(dto.getPassword(), this.passwordEncoder);

      if (passwordIsCorrect) {
        return new UserRO(ResUserError.of("password"));
      }

      /* 
        保存用户登录状态
        Keep user login session state
       */
      session.setAttribute("userId", user.getId());

      return new UserRO(this.buildResUser(user));
    } catch (NoSuchElementException e) {
      /* 
        返还错误信息
        Catch user does not exist
       */
      return new UserRO(ResUserError.of("usernameOrEmail"));
    }
  }

  public UserRO createUser(CreateUserDto dto, HttpSession session) {
    try {
      Password password = Password.encode(
        dto.getPassword(),
        this.passwordEncoder
      );

      User newUser = User.of(dto.getUsername(), dto.getEmail(), password);

      User user = userRepository.save(newUser);

      session.setAttribute("userId", user.getId());

      return new UserRO(this.buildResUser(user));
    } catch (DataIntegrityViolationException e) {
      return new UserRO(ResUserError.of("usernameOrEmail"));
    }
  }

  public ResUser me(Long id) {
    User user = userRepository
      .findById(id)
      .orElseThrow(NoSuchElementException::new);

    return this.buildResUser(user);
  }

  public UserProfileRO fetchUserProfile(
    Long userId,
    Long meId,
    Instant cursor,
    Integer take
  ) {
    /* 
      设置需要插入/更新的初始值 
      Set up initial values to be inserted/updated
     */
    Integer takeAmount = take == null ? 10 : take; // Default amount: 10
    Integer fetchCount = Math.min(takeAmount, 25);
    Integer fetchCountPlusOne = fetchCount + 1;

    Integer offset = cursor == null ? 0 : 1;
    Instant timeFrame = cursor == null ? Instant.now() : cursor;

    /*
      用户未登录，不获取互动状态
      User not loged in, so no interactions
     */
    Boolean isNotLogedIn = meId == null;
    if (isNotLogedIn) {
      List<UserPostInfoWithoutInteractions> userProfileList = userMapper.getUserPostWithoutInteractions(
        offset,
        timeFrame,
        fetchCountPlusOne,
        userId
      );

      return this.buildUserProfileRO(
          userProfileList,
          userId,
          meId,
          fetchCountPlusOne
        );
    }

    /*
      用户登录，获取互动状态
      User loged in, so fetch interactions
     */
    List<UserPostInfoWithInteractions> userProfileList = userMapper.getUserPostWithInteractions(
      offset,
      timeFrame,
      fetchCountPlusOne,
      userId,
      meId
    );

    return buildUserProfileROWithInteractions(
      userProfileList,
      userId,
      meId,
      fetchCountPlusOne
    );
  }

  private ResUser buildResUser(User user) {
    return ResUser.of(
      user.getId(),
      user.getUsername(),
      user.getEmail(),
      user.getCreatedAt(),
      user.getPostAmounts()
    );
  }

  private UserProfileRO buildUserProfileRO(
    List<UserPostInfoWithoutInteractions> userProfileList,
    Long userId,
    Long meId,
    Integer fetchCountPlusOne
  ) {
    Boolean hasMore = userProfileList.size() == fetchCountPlusOne;

    if (hasMore) userProfileList.remove(userProfileList.size() - 1);

    UserPostMapper mapper = Mappers.getMapper(UserPostMapper.class);

    List<UserPostAndInteractions> postAndInteractionsList = new ArrayList<>();

    userProfileList.forEach(
      sourceItem -> {
        String postContent = sourceItem.getContent();

        // 截取帖子内容到50个字符(Slice post content and only send 50 char)
        sourceItem.setContent(sliceContent(postContent));

        UserPostAndInteractions dtoItem = mapper.toPostAndInteractions(
          sourceItem
        );
        postAndInteractionsList.add(dtoItem);
      }
    );

    UserPaginatedPosts userPaginatedPost = new UserPaginatedPosts(
      hasMore,
      postAndInteractionsList
    );

    UserInfo userInfo = buildUserInfo(userProfileList, meId);

    return new UserProfileRO(userInfo, userPaginatedPost);
  }

  private UserProfileRO buildUserProfileROWithInteractions(
    List<UserPostInfoWithInteractions> userProfileList,
    Long userId,
    Long meId,
    Integer fetchCountPlusOne
  ) {
    Boolean hasMore = userProfileList.size() == fetchCountPlusOne;

    if (hasMore) userProfileList.remove(userProfileList.size() - 1);

    UserPostMapper mapper = Mappers.getMapper(UserPostMapper.class);
    List<UserPostAndInteractions> postAndInteractionsList = new ArrayList<>();

    for (UserPostInfoWithInteractions sourceItem : userProfileList) {
      /* 
        截取帖子内容到50个字符 
        Slice post content and only send 50 char
       */
      String postContent = sourceItem.getContent();

      sourceItem.setContent(sliceContent(postContent));

      UserPostAndInteractions dtoItem = mapper.toPostAndInteractions(
        sourceItem
      );
      postAndInteractionsList.add(dtoItem);
    }

    UserPaginatedPosts userPaginatedPost = new UserPaginatedPosts(
      hasMore,
      postAndInteractionsList
    );

    UserInfo userInfo = buildUserInfo(userProfileList, meId);

    return new UserProfileRO(userInfo, userPaginatedPost);
  }

  public String sliceContent(String postContent) {
    Boolean contentNotNullAndLongerThan50 =
      postContent != null && postContent.length() > 50;

    if (contentNotNullAndLongerThan50) {
      postContent = postContent.substring(0, 50);
    }

    return postContent;
  }

  private <T extends UserPostInfoWithoutInteractions> UserInfo buildUserInfo(
    List<T> userProfileList,
    Long meId
  ) {
    T userProfile = userProfileList.get(0);
    String showEmail = meId == userProfile.getId()
      ? userProfile.getEmail()
      : null;

    return new UserInfo(
      userProfile.getId(),
      userProfile.getUserCreatedAt(),
      showEmail,
      userProfile.getPostAmounts(),
      userProfile.getUsername()
    );
  }
}
