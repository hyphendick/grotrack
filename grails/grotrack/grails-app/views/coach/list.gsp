
<%@ page import="grotrack.Coach" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'coach.label', default: 'Coach')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-coach" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-coach" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'coach.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="preferredName" title="${message(code: 'coach.preferredName.label', default: 'Preferred Name')}" />
					
						<g:sortableColumn property="username" title="${message(code: 'coach.username.label', default: 'Username')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${coachInstanceList}" status="i" var="coachInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${coachInstance.id}">${fieldValue(bean: coachInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: coachInstance, field: "preferredName")}</td>
					
						<td>${fieldValue(bean: coachInstance, field: "username")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${coachInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
