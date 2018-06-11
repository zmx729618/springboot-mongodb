package org.zmx.springboot.service;

import java.util.List;

import org.zmx.springboot.domain.HotWordIndex;


public interface HotWordInfoMongoService {
	
	public List<HotWordIndex>  countHotWordLatestOneDay() throws Exception;
	
	public List<HotWordIndex>  countHotWordLatestSevenDays() throws Exception;
	
	public List<HotWordIndex>  countHotWordLatestThirtyDays() throws Exception;

	public void loadHotWordInfo2ES(HotWordIndex hotWordIndex);

	public void loadHotWordInfo2ES(List<HotWordIndex> hotWordIndexList);

	public List<HotWordIndex> findHotWordIndex(Byte type,String date);

}
