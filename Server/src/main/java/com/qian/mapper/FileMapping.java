package com.qian.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.qian.entity.FileEntity;

public interface FileMapping {

	/*
	 * 添加文件进数据库
	 */
	@Insert("insert into `file_table` values(null, #{fileEntity.name}, #{fileEntity.url})")
	public void addFile(@Param("fileEntity") FileEntity fileEntity);
	
	/*
	 * 根据id修改文件
	 */
	@Update("update `file_table` set `name` = #{fileEntity.name}, `url` = #{fileEntity.url} "
			+ "where id = #{id}")
	public void updateFileById(@Param("id") int id, @Param("fileEntity") FileEntity fileEntity);
	
	/*
	 * 根据id删除文件
	 */
	@Delete("delete from `file_table` where id = #{id}")
	public void removeFileById(int id);
	/*
	 * 查询所有文件
	 */
	@Select("select * from `file_table`")
	public List<FileEntity> getFileList();
}
