package com.wap.util;

public class JdbcTemplateUtil{

/*
	@Autowired(required = false)
	private JdbcTemplate jdbcTemplate = null;
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public  List<NetWorkEntity> selectNetWorkBySql(String sql){
		List<NetWorkEntity> list = jdbcTemplate.query(sql,new RowMapper<NetWorkEntity>(){
			public NetWorkEntity mapRow(ResultSet rs, int arg1) throws SQLException {
				NetWorkEntity netWorkEntity = new NetWorkEntity();
				netWorkEntity.setBrandId(rs.getString("brandid"));
				netWorkEntity.setDistance(rs.getDouble("distance"));
				netWorkEntity.setLatitude(rs.getString("latitude"));
				netWorkEntity.setLongitude(rs.getString("longitude"));
				netWorkEntity.setMajorBrandId(rs.getString("majorbrandid"));
				netWorkEntity.setMaxCount(rs.getString("maxcount"));
				netWorkEntity.setNetworkAddress(rs.getString("networkaddress"));
				netWorkEntity.setNetworkJobid(rs.getString("networkjobid"));
				netWorkEntity.setNetworkPhone(rs.getString("networkphone"));
				netWorkEntity.setNetworkType(rs.getString("networktype"));	
				netWorkEntity.setParentNetworkJobid(rs.getString("parentnetworkjobid"));
				netWorkEntity.setRecvmsgPhone(rs.getString("recvmsgphone"));
				netWorkEntity.setNetworkName(rs.getString("networkname"));
				return netWorkEntity;
			}
		});
		return list;
	}*/
}
