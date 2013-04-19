package grotrack



import org.junit.*
import grails.test.mixin.*

@TestFor(CoachController)
@Mock(Coach)
class CoachControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/coach/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.coachInstanceList.size() == 0
        assert model.coachInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.coachInstance != null
    }

    void testSave() {
        controller.save()

        assert model.coachInstance != null
        assert view == '/coach/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/coach/show/1'
        assert controller.flash.message != null
        assert Coach.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/coach/list'

        populateValidParams(params)
        def coach = new Coach(params)

        assert coach.save() != null

        params.id = coach.id

        def model = controller.show()

        assert model.coachInstance == coach
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/coach/list'

        populateValidParams(params)
        def coach = new Coach(params)

        assert coach.save() != null

        params.id = coach.id

        def model = controller.edit()

        assert model.coachInstance == coach
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/coach/list'

        response.reset()

        populateValidParams(params)
        def coach = new Coach(params)

        assert coach.save() != null

        // test invalid parameters in update
        params.id = coach.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/coach/edit"
        assert model.coachInstance != null

        coach.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/coach/show/$coach.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        coach.clearErrors()

        populateValidParams(params)
        params.id = coach.id
        params.version = -1
        controller.update()

        assert view == "/coach/edit"
        assert model.coachInstance != null
        assert model.coachInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/coach/list'

        response.reset()

        populateValidParams(params)
        def coach = new Coach(params)

        assert coach.save() != null
        assert Coach.count() == 1

        params.id = coach.id

        controller.delete()

        assert Coach.count() == 0
        assert Coach.get(coach.id) == null
        assert response.redirectedUrl == '/coach/list'
    }
}
