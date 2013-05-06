package com.twitter.example

import modules.ModulesRepo
import org.specs.SpecificationWithJUnit
import pages._

class PagesRepositorySpec extends SpecificationWithJUnit {
  "testing services" should {

    "inplay module through module repository" in {
      val server = ModulesRepo.Server.buildServer()
      val client = ModulesRepo.Client.buildClient()

      client.moduleFor("inplay")().content mustEqual "<div>Inplay</div>"

      server.close()
    }

    "index page" in {
      val server = Index.Server.buildServer()
      val client = Index.Client.buildClient(server.localAddress)
      client.render()().content mustEqual "asdasdas"

      server.close()
    }
  }
}


