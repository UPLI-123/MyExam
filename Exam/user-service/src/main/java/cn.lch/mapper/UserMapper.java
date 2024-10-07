package cn.lch.mapper;

import cn.lch.entity.User;
import cn.lch.entity.vo.TableUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * (User)表数据库访问层
 *
 * @author lch
 * @since 2022-04-09 19:33:15
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into user(id,name,tel,email,password) VALUES(null,#{name},#{tel},#{email},#{password})")
    public int addUser(User user) ;

//     查询用户的信息
    @Select("select * from user where email =#{v} or tel = #{v}")
    public User selectByUsername(String username) ;

    @Update("update user set password = #{password} where email = #{email} ")
    public int updatePwd(User user) ;

    @Select("select u.*,r.role_name,r.role_id from user u , user_role ur , role r \n" +
            "where u.id = ur.user_id and r.role_id = ur.role_id \n")
    public IPage<User> selectAll(Page page) ;

    @Delete("DELETE from user where id = #{v1}")
    public void delUserById(Integer id) ;

//    @Select("SELECT u.* , s.score FROM `score` s , user u where u.id = s.studentId and s.examCode = 1 and s.score > 0 and s.score < 100")
    @Select("SELECT u.* , s.score FROM `score` s , user u where u.id = s.studentId and s.examCode = #{examCode} and s.score >=90 and s.score <= 100")
    public List<TableUser> getAllTableUserA(@Param("examCode") int examCode) ;

    @Select("SELECT u.* , s.score FROM `score` s , user u where u.id = s.studentId and s.examCode = #{examCode} and s.score >=80 and s.score < 90")
    public List<TableUser> getAllTableUserB(@Param("examCode") int examCode) ;

    @Select(" SELECT u.* , s.score FROM `score` s , user u where u.id = s.studentId and s.examCode = #{examCode} and s.score >=70 and s.score < 80")
    public List<TableUser> getAllTableUserC(@Param("examCode") int examCode) ;

    @Select(" SELECT u.* , s.score FROM `score` s , user u where u.id = s.studentId and s.examCode = #{examCode} and s.score >=60 and s.score < 70")
    public List<TableUser> getAllTableUserD(@Param("examCode") int examCode) ;

    @Select("SELECT u.* , s.score FROM `score` s , user u where u.id = s.studentId and s.examCode = #{examCode} and  s.score <60")
    public List<TableUser> getAllTableUserE(@Param("examCode") int examCode) ;
}

