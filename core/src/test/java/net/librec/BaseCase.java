package net.librec;

import net.librec.conf.Configuration;
import org.junit.After;
import org.junit.Before;



	public class BaseCase {

		protected Configuration conf;

		@Before
		public void setUp() throws Exception {
			conf = new Configuration();
		}

		@After
		public void tearDown() throws Exception {
			conf = new Configuration();
		}

		/**
		 * @return the conf
		 */
		public Configuration getConf() {
			return conf;
		}

	}


