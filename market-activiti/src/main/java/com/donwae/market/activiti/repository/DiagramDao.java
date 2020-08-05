package com.donwae.market.activiti.repository;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface UserDao {

	List<Object> findUser(@Param("keywords") String keywords, @Param("roleType") String roleType);


	List<Object> findUserInOrg(@Param("keywords") String keywords,
                                @Param("orgId") Integer orgId);

    Object getByUserId(Integer userId);

    Object getUserByOa(String oaNo);

	List<Object> getUserListByCondition(Object condition);

    List<Object> queryUserByRole(String roleNm);

    Object getByUserName(String userName);

	Integer addUser(Object user);

	void updateDetail(Object user);

	Integer updateUser(Object user);

    Set<Object> getMembersByOrg(Integer teamId);

    Object getUserAuthData(Map<String, Object> param);

    Object getUserRoleByUser(Object user);

	void insertUserRole(Object ur);

	void updateUserRole(Object ur);

	int getUserTaskStatusCount(@Param("userId") Integer userId, @Param("oaNo") String oaNo, @Param("roleType") String roleType);

    List<Object> queryCrmIdAndUserIdListByCrmIds(@Param("list") List<Integer> crmIds);

    Object queryAdminNormalInfo();

    Object queryNormalInfo(@Param("userId") Integer userId);

    List<Object> queryCrmIdAndUserIdSortByIfDate();

	List<Object> queryCrmIdAndUserIdList();

    List<Object> queryOaNoAndUsrIdList();

	List<Object> queryOaUserIdCrmIdList();

    Integer queryUserIdByOaNo(@Param("oaNo") String oaNo);
}
