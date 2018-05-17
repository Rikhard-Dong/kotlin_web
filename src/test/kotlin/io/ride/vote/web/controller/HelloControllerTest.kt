package io.ride.vote.web.controller

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringJUnit4ClassRunner::class)
@WebMvcTest(HelloController::class)
@AutoConfigureRestDocs(outputDir = "target/vote")
class HelloControllerTest {


    @Autowired
    private var mvc: MockMvc? = null

    @Test
    fun hello() {
        mvc!!.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk)
                .andDo(document("hello"))
    }

}