package com.louddt.tag;

import com.alibaba.fastjson.JSONObject;
import com.louddt.tag.okhttp.OkHttp3Utils;
import com.louddt.tag.utils.DBUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TagApplicationTests {

	@Autowired
	@Qualifier("okHttp3PostUtils")
	private OkHttp3Utils post;

	@Test
	public void contextLoads() {
	}


	@Test
	public void testOlHttp(){
        JSONObject json = new JSONObject();
        String args = "国家税务总局关于跨境应税行为免税备案等增值税问题的公告\n" +
				"国家税务总局公告2017年第30号\n" +
				"现将跨境应税行为免税备案等增值税问题公告如下：\n" +
				"　　一、纳税人发生跨境应税行为，按照《国家税务总局关于发布〈营业税改征增值税跨境应税行为增值税免税管理办法（试行）〉的公告》（国家税务总局公告2016年第29号）的规定办理免税备案手续后发生的相同跨境应税行为，不再办理备案手续。纳税人应当完整保存相关免税证明材料备查。纳税人在税务机关后续管理中不能提供上述材料的，不得享受相关免税政策，对已享受的减免税款应予补缴，并依照《中华人民共和国税收征收管理法》的有关规定处理。\n" +
				"　　二、纳税人以承运人身份与托运人签订运输服务合同，收取运费并承担承运人责任，然后委托实际承运人完成全部或部分运输服务时，自行采购并交给实际承运人使用的成品油和支付的道路、桥、闸通行费，同时符合下列条件的，其进项税额准予从销项税额中抵扣：\n" +
				"　　（一）成品油和道路、桥、闸通行费，应用于纳税人委托实际承运人完成的运输服务；\n" +
				"　　（二）取得的增值税扣税凭证符合现行规定。\n" +
				"　　三、其他个人委托房屋中介、住房租赁企业等单位出租不动产，需要向承租方开具增值税发票的，可以由受托单位代其向主管税务机关按规定申请代开增值税发票。\n" +
				"　　四、自2018年1月1日起，金融机构开展贴现、转贴现业务需要就贴现利息开具发票的，由贴现机构按照票据贴现利息全额向贴现人开具增值税普通发票，转贴现机构按照转贴现利息全额向贴现机构开具增值税普通发票。\n" +
				"　　五、本公告除第四条外，自2017年9月1日起施行，此前已发生未处理的事项，按照本公告规定执行。\n" +
				"　　特此公告。\n" +
				"国家税务总局\n" +
				"2017年8月14日\n" +
				"\n" +
				"　　注释：《国家税务总局关于修改部分税收规范性文件的公告》（国家税务总局公告2018年第31号）对本文进行了修改。";
		json.put("content",args);
		String nlpHttpUrl = "http://192.168.200.33:8888/ner";
        String parm = json.toString();
		long start = System.currentTimeMillis();
		String result = post.request(nlpHttpUrl,parm, OkHttp3Utils.JSON);
		log.info("算法请求成功：用时:" + (System.currentTimeMillis() - start)/1000 + "s");
		log.info("-----------------" + result);
	}



}
