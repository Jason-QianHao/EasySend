<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>Your File List</title>
</head>

<body>
	
	<table>
		<tbody>
			<#if listResource??>
				<#list listResource as p>
					<tr style="font-size: 18px">
						<td>${p.name}</td>
						<td><a href='transWeights?url=${p.name}'>点击下载</a></td>
					</tr>
				</#list>
			</#if>																															
		</tbody>
	</table>
</body>

</html>