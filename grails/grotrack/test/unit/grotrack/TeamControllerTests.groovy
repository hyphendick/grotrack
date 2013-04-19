package grotrack



import org.junit.*
import grails.test.mixin.*

@TestFor(TeamController)
@Mock(Team)
class TeamControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/team/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.teamInstanceList.size() == 0
        assert model.teamInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.teamInstance != null
    }

    void testSave() {
        controller.save()

        assert model.teamInstance != null
        assert view == '/team/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/team/show/1'
        assert controller.flash.message != null
        assert Team.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/team/list'

        populateValidParams(params)
        def team = new Team(params)

        assert team.save() != null

        params.id = team.id

        def model = controller.show()

        assert model.teamInstance == team
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/team/list'

        populateValidParams(params)
        def team = new Team(params)

        assert team.save() != null

        params.id = team.id

        def model = controller.edit()

        assert model.teamInstance == team
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/team/list'

        response.reset()

        populateValidParams(params)
        def team = new Team(params)

        assert team.save() != null

        // test invalid parameters in update
        params.id = team.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/team/edit"
        assert model.teamInstance != null

        team.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/team/show/$team.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        team.clearErrors()

        populateValidParams(params)
        params.id = team.id
        params.version = -1
        controller.update()

        assert view == "/team/edit"
        assert model.teamInstance != null
        assert model.teamInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/team/list'

        response.reset()

        populateValidParams(params)
        def team = new Team(params)

        assert team.save() != null
        assert Team.count() == 1

        params.id = team.id

        controller.delete()

        assert Team.count() == 0
        assert Team.get(team.id) == null
        assert response.redirectedUrl == '/team/list'
    }
}
