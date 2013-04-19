
<%@ page import="grotrack.Workout" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'workout.label', default: 'Workout')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-workout" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-workout" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list workout">
			
				<g:if test="${workoutInstance?.endDate}">
				<li class="fieldcontain">
					<span id="endDate-label" class="property-label"><g:message code="workout.endDate.label" default="End Date" /></span>
					
						<span class="property-value" aria-labelledby="endDate-label"><g:formatDate date="${workoutInstance?.endDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${workoutInstance?.exercises}">
				<li class="fieldcontain">
					<span id="exercises-label" class="property-label"><g:message code="workout.exercises.label" default="Exercises" /></span>
					
						<g:each in="${workoutInstance.exercises}" var="e">
						<span class="property-value" aria-labelledby="exercises-label"><g:link controller="exercise" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${workoutInstance?.frequency}">
				<li class="fieldcontain">
					<span id="frequency-label" class="property-label"><g:message code="workout.frequency.label" default="Frequency" /></span>
					
						<span class="property-value" aria-labelledby="frequency-label"><g:link controller="frequency" action="show" id="${workoutInstance?.frequency?.id}">${workoutInstance?.frequency?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${workoutInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="workout.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${workoutInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${workoutInstance?.startDate}">
				<li class="fieldcontain">
					<span id="startDate-label" class="property-label"><g:message code="workout.startDate.label" default="Start Date" /></span>
					
						<span class="property-value" aria-labelledby="startDate-label"><g:formatDate date="${workoutInstance?.startDate}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${workoutInstance?.id}" />
					<g:link class="edit" action="edit" id="${workoutInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
