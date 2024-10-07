package cn.lch.service.impl;
import cn.lch.entity.Permission;
import cn.lch.mapper.PermissionMapper;
import cn.lch.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * (Permission)表服务实现类
 *
 * @author makejava
 * @since 2022-04-09 22:03:09
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionMapper permissionMapper ;

    @Override
    public List<Map<String, Object>> findTree() {
//        分为两部分 ，第一部分是产需 父菜单
//        permissionMapper.findByIdRoot()
//        创建返回的 list
        List<Map<String, Object>> list = new ArrayList<>();
//       获得 父级菜单
        Set<Permission> allRoot = permissionMapper.getAllRoot();
        for(Permission root : allRoot){
            Map<String,Object> map = new HashMap<>() ;
            map.put("id",root.getPermissionId()) ;
            map.put("label",root.getPermissionName()) ;
            Set<Permission> children = permissionMapper.getChildren(root.getPermissionId());
            List<Map<String, Object>> l1 = new ArrayList<>() ;
            for(Permission p : children){
                Map<String,Object> m1 = new HashMap<>() ;
                m1.put("id",p.getPermissionId()) ;
                m1.put("label",p.getPermissionName()) ;
                l1.add(m1) ;
            }
            map.put("children",l1) ;
            list.add(map) ;
        }

        return list;
    }


//     初始化树操作
    @Override
    public Map<String, Object> initTree(Integer id) {

//        分为两个部分 ，第一个部分是返回当前用户的返回值
        Set<Permission> roots = permissionMapper.getAllP(0,id);
         // 使用两个list 进行存储
        List<Integer> list1 = new ArrayList<>() ;  // 存储展开项
        List<Integer> list2  = new ArrayList<>() ; // 存储选择项
        Map<String,Object> map = new HashMap<>() ;

        for(Permission root :roots){
            list1.add(root.getPermissionId()) ;
            Set<Permission> subRoots = permissionMapper.getAllP( root.getPermissionId(),id);
            for(Permission p: subRoots){
                list2.add(p.getPermissionId()) ;
            }
        }
        map.put("ex",list1) ;
        map.put("ch",list2) ;
        return map;
    }
}

