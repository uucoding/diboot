# Mapper基类与SQL

## 创建一个Mapper类
在项目service/mapper包目录下，创建Mapper接口类，并继承自BaseMapper接口，如：

```java
@Component
public interface MetadataMapper extends BaseMapper{

}
```
## 创建Mapper.xml文件
在同目录下，创建一个对应的同名Mapper.xml文件，如MetadataMapper.xml。
Mapper.xml的Mybatis实现结构如下：
```xml
    <!-- 通用CRUD: begin -->
    <!-- 返回指定列相关的动态SQL -->
	<sql id="columns">
		<trim suffixOverrides=",">
			<if test="f == null or f.id != null">self.id,</if>
			<if test="f == null or f.type != null">self.type,</if>
			<if test="f == null or f.itemName != null">self.item_name,</if>
			<if test="f == null or f.itemValue != null">self.item_value,</if>
			...
		</trim>
	</sql>
    
    <!-- 动态查询条件构建SQL -->
    <sql id="conditions">
		<if test="c.id != null">AND self.id=#{c.id}</if>
		<if test="c.type != null">AND self.type=#{c.type}</if>
		<if test="c.itemValue != null">AND self.item_value=#{c.itemValue}</if>
		<if test="c.itemName != null">AND self.item_name=#{c.itemName}</if>
        AND self.active=1
    </sql>
    
    <!-- 更新指定列的动态SQL -->
    <sql id="setValues">
        <set>
            <if test="f == null or f.type != null">type=#{m.type},</if>
            <if test="f == null or f.itemName != null">item_name=#{m.itemName},</if>
            <if test="f == null or f.itemValue != null">item_value=#{m.itemValue},</if>
        </set>
    </sql>
    
    <!-- 创建记录SQL -->
	<insert id="create" parameterType="com.diboot.framework.model.Metadata" useGeneratedKeys="true" keyProperty="id">
        INSERT INTO metadata(
			parent_id, type, item_name, item_value
		)
		VALUES(
			#{parentId}, #{type}, #{itemName}, #{itemValue}
		)
	</insert>

    <!-- 批量创建记录SQL -->
	<insert id="batchCreate" parameterType="java.util.List" useGeneratedKeys="true" keyProperty="id">
		INSERT INTO metadata(
			parent_id, type, item_name, item_value
		)
		VALUES
		<foreach collection="list" item="m" separator=",">
			(#{m.parentId}, #{m.type}, #{m.itemName}, #{m.itemValue}
		</foreach>
	</insert>
	
    <!-- 更新记录SQL -->
	<update id="update">
		UPDATE metadata
        <include refid="setValues" />
		WHERE id=#{m.id, jdbcType=BIGINT}
	</update>
    
    <!-- 删除记录SQL -->
	<update id="delete" parameterType="Long">
		UPDATE metadata SET active=0 WHERE id=#{id, jdbcType=BIGINT}
	</update>
    
    <!-- 查询一条记录 -->
    <select id="get" parameterType="Long" resultType="com.diboot.framework.model.Metadata">
        SELECT self.*
        FROM metadata self
        WHERE self.id=#{id, jdbcType=BIGINT}
    </select>
    
    <!-- 查询符合条件的记录总数 -->
    <select id="getListCount" parameterType="Map" resultType="Integer">
		SELECT count(self.id)
	    FROM metadata self
	    <where>
            <include refid="conditions" />
	    </where>
	</select>
    
    <!-- 查询符合条件的记录 -->
	<select id="getList" resultType="com.diboot.framework.model.Metadata">
		SELECT self.*
	    FROM metadata self
	    <where>
	    	<include refid="conditions" />
	    </where>
		ORDER BY self.id DESC
		<if test="c.COUNT != null">
			LIMIT <if test="c.OFFSET != null">#{c.OFFSET}, </if>#{c.COUNT}
		</if>
	</select>
    
	<!-- 查询符合条件的记录，返回Map -->
	<select id="getMapList" resultType="java.util.Map">
		SELECT <include refid="columns" />
		FROM metadata self
		<where>
			<include refid="conditions" />
		</where>
		ORDER BY self.id DESC
		<if test="c.COUNT != null">
			LIMIT <if test="c.OFFSET != null">#{c.OFFSET}, </if>#{c.COUNT}
		</if>
	</select>
	
	<!-- 通用CRUD: end -->
	
	<!-- 自定义扩展SQL: begin -->
	    <!-- 通用SQL无法满足的自定义SQL... -->
	<!-- 自定义扩展SQL: end -->
```

如果您启用了diboot开发助理，Mapper及Mapper.xml的代码可跟数据库表保持同步更新，无需手写。
