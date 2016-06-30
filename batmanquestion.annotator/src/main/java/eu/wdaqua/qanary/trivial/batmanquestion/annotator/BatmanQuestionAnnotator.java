package eu.wdaqua.qanary.trivial.batmanquestion.annotator;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.wdaqua.qanary.component.QanaryComponent;
import eu.wdaqua.qanary.component.QanaryMessage;
import eu.wdaqua.qanary.component.QanaryQuestion;
import eu.wdaqua.qanary.component.QanaryUtils;
import eu.wdaqua.qanary.component.ontology.TextPositionSelector;

/**
 * represents the behavior of an annotator of QALD-6 question number 184:
 * "Who created Batman?", i.e., if this question is provided, then it is
 * annotated with the entities (only this question!)
 * 
 * @author AnBo
 *
 */
@Component
public class BatmanQuestionAnnotator extends QanaryComponent {
	private static final Logger logger = LoggerFactory.getLogger(BatmanQuestionAnnotator.class);
	private final String questionToBeAccepted = "Who created Batman?";

	/**
	 * implement this method encapsulating the functionality of your Qanary
	 * component
	 * 
	 * @throws Exception
	 */
	@Override
	public QanaryMessage process(QanaryMessage myQanaryMessage) throws Exception {

		logger.info("process: {}", myQanaryMessage);
		logger.info("received: sparql Endpoint: {}, inGraph: {}, outGraph: {}", myQanaryMessage.getEndpoint(),
				myQanaryMessage.getInGraph(), myQanaryMessage.getOutGraph());

		// TODO: implement processing of question
		// TODO: implement this (custom for every component)

		// TODO: wait for the fully working implementation in QanaryComponent
		QanaryUtils utils = this.getUtils(myQanaryMessage);
		QanaryQuestion<String> question = utils.getQuestion();
		logger.info("question: {} from {}", question.getRawData(), question.getUri());

		if (question.getRawData().toLowerCase().compareTo(questionToBeAccepted.toLowerCase()) == 0) {
			logger.info("question recognized that can be processed: {}", question.getRawData());
			logger.info("apply vocabulary alignment on outgraph for the Batman question");
			this.annotateBatmanQuestionWithPredefinedEntities(utils);
		} else {
			logger.warn("question \"{}\" cannot be processed from this exemplary component, only \"{}\" is accepted.",
					question.getRawData(), this.questionToBeAccepted);
		}

		return myQanaryMessage;
	}

	/**
	 * annotate two entities as expected by QALD-6: "Who created Batman?"
	 * (created, Batman)
	 * 
	 * @throws Exception
	 */
	private void annotateBatmanQuestionWithPredefinedEntities(QanaryUtils utils) throws Exception {
		List<TextPositionSelector> selectors = new LinkedList<>();

		selectors.add(new TextPositionSelector(4, 10));
		selectors.add(new TextPositionSelector(12, 17));

		utils.addAnnotations(selectors);
	}

}