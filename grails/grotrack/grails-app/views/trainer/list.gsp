
<%@ page import="grotrack.Trainer" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'trainer.label', default: 'Trainer')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-trainer" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-trainer" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'trainer.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="preferredName" title="${message(code: 'trainer.preferredName.label', default: 'Preferred Name')}" />
					
						<g:sortableColumn property="username" title="${message(code: 'trainer.username.label', default: 'Username')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${trainerInstanceList}" status="i" var="trainerInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${trainerInstance.id}">${fieldValue(bean: trainerInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: trainerInstance, field: "preferredName")}</td>
					
						<td>${fieldValue(bean: trainerInstance, field: "username")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${trainerInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
