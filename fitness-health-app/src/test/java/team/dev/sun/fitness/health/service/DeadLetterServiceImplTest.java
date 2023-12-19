package team.dev.sun.fitness.health.service;

import team.dev.sun.fitness.health.handler.WebhookHandler;
import team.dev.sun.fitness.health.model.DeadLetter;
import team.dev.sun.fitness.health.model.WebhookPayload;
import team.dev.sun.fitness.health.parser.WebhookPayloadParser;
import team.dev.sun.fitness.health.persistence.DeadLetterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.Future;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeadLetterServiceImplTest {

    @Mock
    private DeadLetterRepository deadLetterRepository;

    @Mock
    private WebhookHandler webhookHandler;

    @Mock
    private WebhookPayloadParser payloadParser;

    @InjectMocks
    private DeadLetterServiceImpl deadLetterService;

    @Mock
    private DeadLetter deadLetter1;

    @Mock
    private DeadLetter deadLetter2;

    @Mock
    private WebhookPayload webhookPayload;

    @Mock
    private Future<Boolean> future;

    @Test
    void processAllLetters() throws Exception {

        when(deadLetterRepository.getUnprocessedIds()).thenReturn(List.of(12L, 17L));
        when(deadLetterRepository.getReferenceById(anyLong())).thenReturn(deadLetter1, deadLetter2);
        when(deadLetter1.getData()).thenReturn("data 1");
        when(deadLetter2.getData()).thenReturn("data 2");
        when(payloadParser.toWebhookPayload(anyString())).thenReturn(webhookPayload);
        when(webhookHandler.submitPayload(any())).thenReturn(future);
        when(future.get()).thenReturn(true);
        when(future.isDone()).thenReturn(true);

        int count = deadLetterService.reprocessUnprocessed();

        assertEquals(2, count);

        verify(deadLetter1).getData();
        verify(deadLetter1).setProcessed(eq(true));
        verify(deadLetter1).setProcessedAt(any());

        verify(deadLetter2).getData();
        verify(deadLetter2).setProcessed(eq(true));
        verify(deadLetter2).setProcessedAt(any());

        verify(deadLetterRepository).getUnprocessedIds();
        verify(deadLetterRepository, times(2)).save(any());
        verify(webhookHandler, times(2)).submitPayload(any());
    }

    @Test
    void skipProcessingAsThereIsNoUnprocessedLetters() {

        when(deadLetterRepository.getUnprocessedIds()).thenReturn(emptyList());
        int count = deadLetterService.reprocessUnprocessed();
        assertEquals(0, count);
        verifyNoInteractions(webhookHandler);
        verify(deadLetterRepository).getUnprocessedIds();
        verifyNoMoreInteractions(deadLetterRepository);
    }
}