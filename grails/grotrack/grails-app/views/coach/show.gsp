
<%@ page import="grotrack.Coach" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'coach.label', default: 'Coach')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-coach" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-coach" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list coach">
			
				<g:if test="${coachInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="coach.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${coachInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${coachInstance?.preferredName}">
				<li class="fieldcontain">
					<span id="preferredName-label" class="property-label"><g:message code="coach.preferredName.label" default="Preferred Name" /></span>
					
						<span class="property-value" aria-labelledby="preferredName-label"><g:fieldValue bean="${coachInstance}" field="preferredName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${coachInstance?.username}">
				<li class="fieldcontain">
					<span id="username-label" class="property-label"><g:message code="coach.username.label" default="Username" /></span>
					
						<span class="property-value" aria-labelledby="username-label"><g:fieldValue bean="${coachInstance}" field="username"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${coachInstance?.id}" />
					<g:link class="edit" action="edit" id="${coachInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
