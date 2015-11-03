package com.virdis

/**
  * Created by sandeep on 11/2/15.
  */

import org.joda.time.DateTime
import org.scalatest._

class TweetParserSpec extends MySpec {

  def fixture = {
    new {
      val tweetWithHTags =
        """
          |{
          |    "created_at": "Fri Oct 30 15:29:45 +0000 2015",
          |    "id": 660116385083838500,
          |    "id_str": "660116385083838464",
          |    "text": "Have you got your #pumpkin ready for #Halloween Have Fun! #HappyHalloween #happyfriday https://t.co/UIo2LeYPoZ https://t.co/vamv9XSsH4",
          |    "source": "<a href=\"http://twitter.com\" rel=\"nofollow\">Twitter Web Client</a>",
          |    "truncated": false,
          |    "entities": {
          |        "hashtags": [
          |            {
          |                "text": "pumpkin",
          |                "indices": [
          |                    18,
          |                    26
          |                ]
          |            },
          |            {
          |                "text": "Halloween",
          |                "indices": [
          |                    37,
          |                    47
          |                ]
          |            },
          |            {
          |                "text": "HappyHalloween",
          |                "indices": [
          |                    58,
          |                    73
          |                ]
          |            },
          |            {
          |                "text": "happyfriday",
          |                "indices": [
          |                    74,
          |                    86
          |                ]
          |            }
          |        ]
          |    },
          |    "timestamp_ms": "1446218985230"
          |}
        """.stripMargin

      val tweetWithNoTags =
        """
          |{
          |    "created_at": "Thu Oct 29 17:51:50 +0000 2015",
          |    "id": 660116385083838500,
          |    "id_str": "660116385083838464",
          |    "text": "Have you got your #pumpkin ready for #Halloween Have Fun! #HappyHalloween #happyfriday https://t.co/UIo2LeYPoZ https://t.co/vamv9XSsH4",
          |    "source": "<a href=\"http://twitter.com\" rel=\"nofollow\">Twitter Web Client</a>",
          |    "truncated": false,
          |    "entities": {
          |        "hashtags": []
          |    },
          |    "timestamp_ms": "1446218985230"
          |}
        """.stripMargin
    }


  }

  "TweetParser" should "parse tweets" in {
    val f = fixture
    val sf = tweetParser.secondFeatureFormat(f.tweetWithHTags).get
    sf.hashtags.toSet should be === Set("pumpkin", "happyfriday", "HappyHalloween".toLowerCase(), "Halloween".toLowerCase())
    sf.created_at shouldBe a [DateTime]
  }

  it should "parse tweets with empty hashtags" in {
    val f = fixture
    val sf = tweetParser.secondFeatureFormat( f.tweetWithNoTags ).get
    sf.hashtags should have size 0
  }
}
