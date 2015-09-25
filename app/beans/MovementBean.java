package beans;

import java.util.List;
/**
 * 接口数据
 *
 * @author boxizen
 * @since 2015/07/25
 */
public class MovementBean {

    public String id;

    public int time;

    public MovementBean(String id,int time){
      this.id = id;
      this.time = time;
    }
}
