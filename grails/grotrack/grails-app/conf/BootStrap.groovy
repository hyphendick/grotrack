class BootStrap {

    def init = { servletContext ->
    	def workout1 = new grotrack.Workout(name: 'workout 1').save()
        def workout2 = new grotrack.Workout(name: 'Workout 2').save()
    }
    def destroy = {
    }
}
