<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>Your Copy List</title>
</head>

<body>
	
	<table>
		<tbody>
			<#if listResource??>
				<#list listResource as p>
					<tr style="font-size: 18px">
						<td>${p}</td>
					</tr>
				</#list>
			</#if>																															
		</tbody>
	</table>
</body>

</html>