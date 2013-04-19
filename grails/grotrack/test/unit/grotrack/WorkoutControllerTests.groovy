package grotrack



import org.junit.*
import grails.test.mixin.*

@TestFor(WorkoutController)
@Mock(Workout)
class WorkoutControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/workout/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.workoutInstanceList.size() == 0
        assert model.workoutInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.workoutInstance != null
    }

    void testSave() {
        controller.save()

        assert model.workoutInstance != null
        assert view == '/workout/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/workout/show/1'
        assert controller.flash.message != null
        assert Workout.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/workout/list'

        populateValidParams(params)
        def workout = new Workout(params)

        assert workout.save() != null

        params.id = workout.id

        def model = controller.show()

        assert model.workoutInstance == workout
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/workout/list'

        populateValidParams(params)
        def workout = new Workout(params)

        assert workout.save() != null

        params.id = workout.id

        def model = controller.edit()

        assert model.workoutInstance == workout
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/workout/list'

        response.reset()

        populateValidParams(params)
        def workout = new Workout(params)

        assert workout.save() != null

        // test invalid parameters in update
        params.id = workout.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/workout/edit"
        assert model.workoutInstance != null

        workout.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/workout/show/$workout.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        workout.clearErrors()

        populateValidParams(params)
        params.id = workout.id
        params.version = -1
        controller.update()

        assert view == "/workout/edit"
        assert model.workoutInstance != null
        assert model.workoutInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/workout/list'

        response.reset()

        populateValidParams(params)
        def workout = new Workout(params)

        assert workout.save() != null
        assert Workout.count() == 1

        params.id = workout.id

        controller.delete()

        assert Workout.count() == 0
        assert Workout.get(workout.id) == null
        assert response.redirectedUrl == '/workout/list'
    }
}
