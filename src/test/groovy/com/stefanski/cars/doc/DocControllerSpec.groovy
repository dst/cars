package com.stefanski.cars.doc

import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * @author Dariusz Stefanski
 */
class DocControllerSpec extends Specification {

    MockMvc mockMvc

    void setup() {
        DocController controller = new DocController()
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    def "should empty path redirect to swagger doc"() {
        expect:
            mockMvc.perform(get("/"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/docs/index.html"));
    }
}