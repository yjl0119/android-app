package uk.ac.ncl.openlab.irismsg.api

import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import uk.ac.ncl.openlab.irismsg.MemberRole
import uk.ac.ncl.openlab.irismsg.model.ApiEntity

class EntityGeneratorTest {
    
    lateinit var generator: EntityGenerator
    
    @Before fun setup () {
        generator = EntityGenerator()
    }
    
    fun <T : ApiEntity> testEntity (entity: T) {
        assertNotNull(entity.id)
        assertNotNull(entity.createdAt)
        assertNotNull(entity.updatedAt)
    }
    
    
    @Test fun makeId_createsAnId () {
        assertEquals("1", generator.makeId())
    }
    @Test fun makeId_createsAUniqueId () {
        generator.makeId()
        assertEquals("2", generator.makeId())
    }
    
    
    @Test fun makeUser_isAnEntity() {
        testEntity(generator.makeUser(UserGen.CURRENT))
    }
    @Test fun makeUser_createsACurrentUser () {
        val user = generator.makeUser(UserGen.CURRENT)
        assertEquals(EntityGenerator.currentUserId, user.id)
        assertNotNull(user.verifiedOn)
        assertNotNull(user.fcmToken)
    }
    @Test fun makeUser_createsAnUnverifiedUser () {
        val user = generator.makeUser(UserGen.UNVERIFIED)
        assertEquals("1", user.id)
        assertNull(user.verifiedOn)
        assertNull(user.fcmToken)
    }
    
    
    @Test fun makeUserAuth_hasAToken () {
        val userAuth= generator.makeUserAuth()
        assertEquals("some-really-long-jsonwebtoken", userAuth.token)
    }
    @Test fun makeUserAuth_hasAUser () {
        val userAuth= generator.makeUserAuth()
        assertNotNull(userAuth.user)
    }
    
    
    @Test fun makeOrganisation_isAnEntity () {
        testEntity(generator.makeOrganisation())
    }
    @Test fun makeOrganisation_hasInfo () {
        val org = generator.makeOrganisation()
        assertEquals("Organisation A", org.name)
    }
    @Test fun makeOrganisation_addsMembers () {
        val org = generator.makeOrganisation()
        assertEquals(4, org.members.size)
    }
    @Test fun makeOrganisation_addsCoordinator () {
        val org = generator.makeOrganisation()
        assertEquals(EntityGenerator.currentUserId, org.members[0].userId)
        assertEquals(MemberRole.COORDINATOR, org.members[0].role)
    }
    @Test fun makeOrganisation_addsDonor() {
        val org = generator.makeOrganisation()
        assertEquals(EntityGenerator.currentUserId, org.members[1].userId)
        assertEquals(MemberRole.DONOR, org.members[1].role)
    }
    @Test fun makeOrganisation_addsSubScribers() {
        val org = generator.makeOrganisation()
        assertEquals(MemberRole.SUBSCRIBER, org.members[2].role)
        assertEquals(MemberRole.SUBSCRIBER, org.members[3].role)
    }
    
    
    @Test fun makeMember_isAnEntity () {
        testEntity(generator.makeMember(MemberRole.SUBSCRIBER, UserGen.CURRENT))
    }
    @Test fun makeMember_setsRole () {
        val member = generator.makeMember(MemberRole.SUBSCRIBER, UserGen.CURRENT)
        assertEquals(MemberRole.SUBSCRIBER, member.role)
    }
    @Test fun makeMember_createsForACurrentUser () {
        var member = generator.makeMember(MemberRole.SUBSCRIBER, UserGen.CURRENT)
        assertEquals(EntityGenerator.currentUserId, member.userId)
        assertNotNull(member.confirmedOn)
        assertNull(member.deletedOn)
    }
    @Test fun makeMember_createsForAnUnverifiedUser () {
        var member = generator.makeMember(MemberRole.SUBSCRIBER, UserGen.UNVERIFIED)
        assertNotEquals(EntityGenerator.currentUserId, member.userId)
        assertNull(member.confirmedOn)
    }
    
    
    @Test fun makeMessage_isAnEntity () {
        val message = generator.makeMessage("1")
        testEntity(message)
    }
    @Test fun makeMessage_isInfo () {
        val message = generator.makeMessage("1")
        assertEquals("Hello, World!", message.content)
        assertEquals("1", message.organisationId)
        assertEquals(EntityGenerator.currentUserId, message.authorId)
    }
}